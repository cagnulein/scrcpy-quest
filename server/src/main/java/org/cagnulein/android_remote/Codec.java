package org.cagnulein.android_remote;

public interface Codec {

    enum Type {
        VIDEO,
        AUDIO,
    }

    Type getType();

    int getId();

    String getName();

    String getMimeType();
}