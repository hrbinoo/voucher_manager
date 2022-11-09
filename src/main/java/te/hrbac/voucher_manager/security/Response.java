package te.hrbac.voucher_manager.security;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersion = 9593485954535L;
    private final String jwttoken;

    public Response(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getJwttoken() {
        return jwttoken;
    }
}
