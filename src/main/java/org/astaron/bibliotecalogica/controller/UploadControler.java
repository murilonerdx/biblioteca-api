package org.astaron.bibliotecalogica.controller;

import jakarta.activation.FileTypeMap;
import jakarta.servlet.http.HttpServletRequest;
import org.astaron.bibliotecalogica.config.FileStorageProperties;
import org.astaron.bibliotecalogica.model.CategoriaEnum;
import org.astaron.bibliotecalogica.model.Livro;
import org.astaron.bibliotecalogica.request.LivroUploadRequest;
import org.astaron.bibliotecalogica.response.FileResponse;
import org.astaron.bibliotecalogica.response.LivroResponse;
import org.astaron.bibliotecalogica.service.LivroService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class UploadControler {
	private final LivroService livroService;
	private final Path fileLocation;
	static final String PATH_LOCAL_IMG = "src/main/resources/upload/";

	public UploadControler(LivroService livroService, FileStorageProperties fileStorageProperties) {
		this.fileLocation = Paths.get(PATH_LOCAL_IMG).toAbsolutePath().normalize();
		this.livroService = livroService;
	}

	@RequestMapping(value = "/download/{linkLivro}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> pdf(@PathVariable("linkLivro") String pdf) throws IOException {
		File file = new File(PATH_LOCAL_IMG + pdf);
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=" + file.getName())
				.contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
				.body(Files.readAllBytes(file.toPath()));
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<FileResponse> upload(@RequestParam String descricao,
											   @RequestParam CategoriaEnum categoria,
											   @RequestParam("pdf") MultipartFile pdf,
											   HttpServletRequest httpRequest) throws IOException {
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));

		String fileDownloadUri = ServletUriComponentsBuilder.fromContextPath(httpRequest)
				.path("/api/v1/download/")
				.path(fileName)
				.toUriString();

		try {
			Path targetLocation = fileLocation.resolve(fileName).normalize();
			Files.copy(pdf.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			Livro livroSave = livroService.saveBook(new LivroUploadRequest(fileName, descricao, categoria, fileDownloadUri, pdf.getSize()));
			LivroResponse livroResponse = new LivroResponse(livroSave);

			return ResponseEntity.ok().body(FileResponse.builder()
					.livro(livroResponse)
					.uri(fileDownloadUri)
					.size(pdf.getSize())
					.build());
		} catch (Exception ex) {
			return ResponseEntity.internalServerError().build();
		}

	}

	@RequestMapping(value = "/book/{linkLivro}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getBook(@PathVariable("linkLivro") String imagem) throws IOException {
		File img = new File(PATH_LOCAL_IMG + imagem);
		return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
	}


}
