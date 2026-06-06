package com.course.event_service.entities.types;

public enum Category {
    PARTY(0),
    CONCERT(1),
    SPORTS(2),
    GASTRONOMY(3);

    private int code;

    Category(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Category valueOf(int code) {
        for (Category value : Category.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Category code");
    }
}
