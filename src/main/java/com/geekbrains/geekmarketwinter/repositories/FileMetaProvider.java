package com.geekbrains.geekmarketwinter.repositories;

import com.geekbrains.geekmarketwinter.entites.FileMetaDTO;
import com.geekbrains.geekmarketwinter.repositories.interfaces.IFileMetaProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.UUID;


@Component
public class FileMetaProvider implements IFileMetaProvider {

    private static final String GET_FILES_META = "select hash, filename from geek.file_info_metadata where sub_type = :subtype";

    private static final String DELETE_FILE_BY_HASH_AND_NAME = "delete from geek.file_info_metadata where hash = :hash and filename = :filename";

    private static final String GET_FILE_PATH_BY_HASH = "select filename from geek.file_info_metadata where hash = :hash";

    private static final String EXIST_FILE_BY_HASH_NAME = "select filename from geek.file_info_metadata where hash = :hash and filename = :filename";

    private static final String SAVE_FILE_META_DATA = "insert into geek.file_info_metadata (hash, filename, sub_type)\n" +
            "values (:hash, :finame, :subtype)";

    private final Sql2o sql2o;

    public FileMetaProvider(@Autowired Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public String checkFileExists(UUID fileHash) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(GET_FILE_PATH_BY_HASH, false)
                    .addParameter("hash", fileHash)
                    .executeScalar(String.class);
        }
    }

    @Override
    public String checkFileExists(UUID fileHash, String filename) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(EXIST_FILE_BY_HASH_NAME, false)
                    .addParameter("hash", fileHash)
                    .addParameter("filename", filename)
                    .executeScalar(String.class);
        }
    }

    @Override
    public void saveFileMeta(UUID fileHash, String fileName, int sybType) {
        try (Connection connection = sql2o.open()) {
            connection.createQuery(SAVE_FILE_META_DATA)
                    .addParameter("hash", fileHash)
                    .addParameter("finame", fileName)
                    .addParameter("subtype", sybType)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteFile(UUID fileHash, String filename) {
        try (Connection connection = sql2o.open()) {
            connection.createQuery(DELETE_FILE_BY_HASH_AND_NAME)
                    .addParameter("hash", fileHash)
                    .addParameter("filename", filename)
                    .executeUpdate();
        }
    }

    @Override
    public Collection<FileMetaDTO> getMetaFiles(int subtype) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(GET_FILES_META, false)
                    .addParameter("subtype", subtype)
                    .executeAndFetch(FileMetaDTO.class);
        }
    }

}