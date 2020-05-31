package com.business.manager.horario.exceptions;

import com.business.manager.horario.exceptions.errors.ErrorEnum;

public class OperationNotPossibleException extends BaseException {
	private static final long serialVersionUID = 1L;

	public OperationNotPossibleException(ErrorEnum error, Object... args) {
		super(error, args);
	}
}
