package org.astaron.bibliotecalogica.response;

import lombok.Builder;

@Builder
public record FileResponse(LivroResponse livro, String uri, Long size){}
