package org.astaron.bibliotecalogica.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse implements Serializable {
	private Date timestamp;
	private String message;
	private String details;
}
