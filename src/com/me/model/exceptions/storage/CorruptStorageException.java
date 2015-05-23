package com.me.model.exceptions.storage;


import com.me.model.exceptions.BaseException;

public class CorruptStorageException extends BaseException {
    public CorruptStorageException() {
        super("Storage is corrupt!");
    }

    public CorruptStorageException(String message) {
        super(message);
    }
}
