package org.example.doubles;

import org.example.export.interfaces.FileWriter;

public class FileWriterSpy implements FileWriter {

    public String lastName;
    public String lastContent;
    public int calls = 0;

    @Override
    public void save(String fileName, String content) {
        this.lastName = fileName;
        this.lastContent = content;
        calls++;
    }
}
