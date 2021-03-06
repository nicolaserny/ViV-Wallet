package com.invivoo.vivwallet.api.application.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invivoo.vivwallet.api.ViVWalletApiApplication;
import com.invivoo.vivwallet.api.domain.role.RoleType;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class JWTUserDetailsTest {

    private static ObjectMapper objectMapper = new ViVWalletApiApplication().objectMapper();

    @Test
    public void shouldHaveCorrectJWTUserDetails_whenBuildFromDecodedJWT() {
        // given
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);

        Claim userClaim = mockVivWalletClaims("John DOE", Claim::asString);
        Mockito.when(decodedJWT.getClaim("user")).thenReturn(userClaim);

        Mockito.when(decodedJWT.getClaim("viv-wallet")).thenReturn(mockVivWalletClaims());

        // when
        Optional<JWTUserDetails> jwtUserDetails = JWTUserDetails.fromDecodedJWT(decodedJWT, objectMapper);

        // then
        Assert.assertTrue(jwtUserDetails.isPresent());
        JWTUserDetails details = jwtUserDetails.get();
        Assert.assertEquals("1", details.getUsername());
        Assert.assertEquals(List.of(new RoleGrantedAuthority(RoleType.EXPERTISE_MANAGER),
                                    new RoleGrantedAuthority(RoleType.COMPANY_ADMINISTRATOR),
                                    new RoleGrantedAuthority(RoleType.SENIOR_MANAGER),
                                    new RoleGrantedAuthority(RoleType.CONSULTANT)), details.getAuthorities());

    }

    private Claim mockVivWalletClaims() {
        return new Claim() {
            @Override
            public boolean isNull() {
                return false;
            }

            @Override
            public Boolean asBoolean() {
                return null;
            }

            @Override
            public Integer asInt() {
                return null;
            }

            @Override
            public Long asLong() {
                return null;
            }

            @Override
            public Double asDouble() {
                return null;
            }

            @Override
            public String asString() {
                return "{\"roles\":[\"EXPERTISE_MANAGER\",\"COMPANY_ADMINISTRATOR\",\"SENIOR_MANAGER\",\"CONSULTANT\"],\"userId\":1}";
            }

            @Override
            public Date asDate() {
                return null;
            }

            @Override
            public <T> T[] asArray(Class<T> aClass) throws JWTDecodeException {
                return null;
            }

            @Override
            public <T> List<T> asList(Class<T> aClass) throws JWTDecodeException {
                return null;
            }

            @Override
            public Map<String, Object> asMap() throws JWTDecodeException {
                return null;
            }

            @Override
            public <T> T as(Class<T> aClass) throws JWTDecodeException {
                return null;
            }


        };
    }

    private <T> Claim mockVivWalletClaims(T value, Function<Claim, T> supplier) {
        Claim roleClaim = Mockito.mock(Claim.class);
        Mockito.when(supplier.apply(roleClaim)).thenReturn(value);
        return roleClaim;
    }
}
