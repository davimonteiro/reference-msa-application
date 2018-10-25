package demo.beethoven;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContextualInput {
    private String key;
    private String value;

    public ContextualInput(OAuth2AccessToken accessToken) {
        String token = accessToken.getTokenType() + " " + accessToken.getValue();
        this.key = "${access_token}";
        this.value = token;
    }
}
