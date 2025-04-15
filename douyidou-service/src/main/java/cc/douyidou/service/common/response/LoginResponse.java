package cc.douyidou.service.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginResponse implements Serializable {

    private static final long serialVersionUID=1L;

    private String accessToken;
    private String refreshToken;
    private int expire;
}
