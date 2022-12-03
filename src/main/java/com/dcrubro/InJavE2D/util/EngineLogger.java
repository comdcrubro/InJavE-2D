package com.dcrubro.InJavE2D.util;

import org.apache.logging.log4j.*;

public class EngineLogger {

    private String _class;

    public EngineLogger(String _class) {
        this._class = _class;
    }

    public void InfoLog(String message) {
        LogManager.getLogger().log(Level.INFO, _class + ": " + message);
    }
}
