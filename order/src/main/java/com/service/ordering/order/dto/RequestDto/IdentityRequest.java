package com.service.ordering.order.dto.RequestDto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdentityRequest {
    public Integer userId;
}
