package com.prime.redef.app.controllers;

public interface IFrameController {
    void setTitle(String title);
    CharSequence getTitle();
    void setSubtitle(String subtitle);
    CharSequence getSubtitle();
    void resetTitle();
    void setHomeButton(boolean button);
}
