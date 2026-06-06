package com.course.event_service.entities.types;

public enum Modality {
    IN_PERSON(0),
    ONLINE(1);

    private int code;

    Modality(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Modality valueOf(int code) {
        for (Modality value : Modality.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Modality code");
    }
}