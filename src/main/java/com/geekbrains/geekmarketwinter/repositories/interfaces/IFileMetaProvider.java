package com.geekbrains.geekmarketwinter.repositories.interfaces;

import com.geekbrains.geekmarketwinter.entites.FileMetaDTO;

import java.util.Collection;
import java.util.UUID;

public interface IFileMetaProvider {

    String checkFileExists(UUID fileHash);

    String checkFileExists(UUID fileHash, String name);

    /**
     * Сохранить метаданные файла
     *
     */
    void saveFileMeta(UUID Hash, String fileName, int sybType);

    void deleteFile(UUID fileHash, String filename);

    Collection<FileMetaDTO> getMetaFiles(int subtype);
}
