package com.lvv.bulletinboard.to;

import com.lvv.bulletinboard.HasIdAndEmail;
import com.lvv.bulletinboard.util.validation.NoHtml;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserTo extends BaseTo implements HasIdAndEmail {
    @Email
    @NotBlank
    @Size(max = 100)
    @NoHtml  // https://stackoverflow.com/questions/17480809
    String email;

    @NotBlank
    @Size(min = 5, max = 32)
    String password;

    @NotBlank
    @Size(min = 4, max = 32)
    String role;

    public UserTo() {
        super();
    }

    public UserTo(Integer id, String email, String password, String role) {
        super(id);
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
