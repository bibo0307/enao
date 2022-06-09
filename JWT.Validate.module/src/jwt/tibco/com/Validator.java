package jwt.tibco.com;

import java.util.Date;
import java.util.Vector;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;


public class Validator {	
	private static String JWT_Type = "JWT";
	
	protected boolean validated;
	protected Object[] claims;
	
	public Validator() {
		setValidated(false);
		setClaims(null);
	}
	
	public void validate(String token, String secret, String issuer, String audience, String subject) {
		DecodedJWT jwt = null;
		setValidated(false);
		
		if (token == null || secret == null || issuer == null || audience == null || subject == null)
			return;
		
		try {
			jwt = JWT.require(Algorithm.HMAC256(secret.getBytes())).build().verify(token);
		} catch (JWTVerificationException e) {
			return;
		}
		
		if (jwt == null || jwt.getType() == null || !jwt.getType().contentEquals(JWT_Type))
			return;
		
		if (!jwt.getIssuer().contentEquals(issuer) ||
			!jwt.getAudience().contains(audience) ||
			!jwt.getSubject().contentEquals(subject))
			return;
		
		Date now = new Date();
		
		if ((jwt.getNotBefore() != null && jwt.getNotBefore().after(now)) ||
			(jwt.getExpiresAt() != null && jwt.getExpiresAt().before(now)))
			return;
		
		setValidated(true);

		Map<String, Claim> claimsMap = jwt.getClaims();
		Vector<jwt.tibco.com.Claim> claimsVector = new Vector<jwt.tibco.com.Claim>();
		
		if (claimsMap != null) {
			for (Map.Entry<String, Claim> entry : claimsMap.entrySet()) {
				String key = entry.getKey();
				if (key != null && !key.matches("aud|sub|iss|exp|iat")) {					
					claimsVector.add(new jwt.tibco.com.Claim(key, entry.getValue().asString()));
				}
			}
			
		}

		setClaims(claimsVector.isEmpty() ? null : claimsVector.toArray());
	}

	public boolean isValidated() { return validated; }
	public void setValidated(boolean val) { validated = val; }

	public Object[] getClaims() { return claims; }
	public void setClaims(Object[] val) { claims = (val == null ? new Object[0] : val); }
}
