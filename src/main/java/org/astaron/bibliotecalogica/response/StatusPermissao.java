package org.astaron.bibliotecalogica.response;

import org.astaron.bibliotecalogica.model.enums.TipoPermissaoEnum;

public record StatusPermissao(TipoPermissaoEnum status, String motivo) {
}
