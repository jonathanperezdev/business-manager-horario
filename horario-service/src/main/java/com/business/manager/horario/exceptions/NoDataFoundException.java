package com.business.manager.horario.exceptions;

import com.business.manager.horario.exceptions.errors.ErrorEnum;

public class NoDataFoundException extends BaseException {
	
	public NoDataFoundException(ErrorEnum error, Object... args) {
		super(error, args);
	}

}
