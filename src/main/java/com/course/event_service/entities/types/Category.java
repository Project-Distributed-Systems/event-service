package com.course.event_service.entities.types;

public enum Category {
    PARTY(1),
    CONCERT(2),
    SPORTS(3),
    GASTRONOMY(4);

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
