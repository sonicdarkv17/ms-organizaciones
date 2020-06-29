package com.smart.wsorg.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1250166508152483573L;
	private String token;
}
