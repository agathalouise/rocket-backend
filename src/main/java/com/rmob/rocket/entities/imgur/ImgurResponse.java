package com.rmob.rocket.entities.imgur;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ImgurResponse {

	private ImgurData data;
	private boolean success;
	private int status;
}
