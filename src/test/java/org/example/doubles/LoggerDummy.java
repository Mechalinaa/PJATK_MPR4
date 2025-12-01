package org.example.doubles;

import org.example.training.interfaces.LogTool;

public class LoggerDummy implements LogTool {
    @Override
    public void log(String msg) {
        // dummy - nic nie robi
    }
}
