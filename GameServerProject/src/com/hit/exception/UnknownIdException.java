package com.hit.exception;

import java.io.Serializable;

//thats a special exception designed for our gameserver app.
//if the id that was entered doesnt match to any id in running games hashmap located in game service,
//we throw this exception.

public class UnknownIdException extends Exception implements Serializable {
	public UnknownIdException(java.lang.Throwable err) {
		super(err);
	}
	public UnknownIdException(java.lang.Throwable err, java.lang.String message) {
		super(message,err);
	}
}
