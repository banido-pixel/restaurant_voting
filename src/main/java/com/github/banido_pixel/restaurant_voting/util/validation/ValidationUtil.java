package com.github.banido_pixel.restaurant_voting.util.validation;

import com.github.banido_pixel.restaurant_voting.HasId;
import com.github.banido_pixel.restaurant_voting.error.IllegalRequestDataException;
import com.github.banido_pixel.restaurant_voting.error.IllegalTimeException;
import com.github.banido_pixel.restaurant_voting.error.NotFoundException;
import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;

import java.time.Clock;
import java.time.LocalTime;

@UtilityClass
public class ValidationUtil {

    public static final LocalTime UPDATE_END_TIME = LocalTime.of(11, 0);

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void assureTimeValid(Clock clock) {
        LocalTime time = LocalTime.now(clock);
        if (time.isAfter(UPDATE_END_TIME)) {
            throw new IllegalTimeException("Vote cannot be updated after " + UPDATE_END_TIME);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}