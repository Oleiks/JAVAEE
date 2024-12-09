package com.example.demo.authentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
//@BasicAuthenticationMechanismDefinition(realmName = "Music application")
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/authentication/custom/login.xhtml",
                errorPage = "/authentication/custom/login_error.xhtml"
        )
)
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/MusicApplication",
        callerQuery = "select password from authors where name = ?",
        groupsQuery = "select role from users__roles where id = (select id from authors where name = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {
}
