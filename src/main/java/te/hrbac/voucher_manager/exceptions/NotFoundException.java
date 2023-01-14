package te.hrbac.voucher_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException(Class<?> entityClass, Object property, Object value){
        super(String.format("Could not find entity of type %s with '%s' of value '%s'",
                    entityClass.getName(), property, value
                ));
    }
}
