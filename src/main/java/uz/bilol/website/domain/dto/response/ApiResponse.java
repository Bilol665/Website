package uz.bilol.website.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    static public ApiResponse create(BindingResult bindingResult) {
        ApiResponse apiResponse = new ApiResponse();
        StringBuilder errors = new StringBuilder();
        bindingResult.getFieldErrors().forEach(fieldError -> errors
                .append(fieldError.getField())
                .append(" : ").append(fieldError.getDefaultMessage())
                .append("  "));
        apiResponse.setMessage(errors.toString());
        apiResponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
        apiResponse.setSuccess(Boolean.FALSE);
        apiResponse.setData(null);
        return apiResponse;
    }

    private HttpStatus status;
    private boolean success;
    private String message;
    private Object data;

    public ApiResponse(HttpStatus status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }
}
