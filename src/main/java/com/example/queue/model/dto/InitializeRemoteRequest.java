package com.example.queue.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitializeRemoteRequest {

    @JsonProperty(required = true)
    private String remoteName;

    @JsonProperty(required = false)
    private String port;

    @JsonProperty(required = true)
    private String remotePass;

    @JsonProperty(required = true)
    private String remoteKey;
}
