package com.gamsung.backend.domain.cart.controller;

import com.gamsung.backend.domain.cart.dto.request.CartDeleteRequest;
import com.gamsung.backend.domain.cart.dto.request.CartEntryRequest;
import com.gamsung.backend.domain.cart.dto.response.CartDeleteResponse;
import com.gamsung.backend.domain.cart.dto.response.CartEntryResponse;
import com.gamsung.backend.domain.cart.dto.response.CartFindResponse;
import com.gamsung.backend.domain.cart.service.CartService;
import com.gamsung.backend.global.common.ApiResponse;
import com.gamsung.backend.global.resolver.AuthContext;
import com.gamsung.backend.global.resolver.MemberAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import java.util.List;

@Tag(name = "장바구니")
@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    @Operation(summary = "장바구니에 상품 추가 API", description = "로그인한 회원의 장바구니에 상품을 추가할 때 쓰는 API"
    ,security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<CartEntryResponse>> entryMyCart(@Valid @RequestBody CartEntryRequest cartEntryRequest
    , @Parameter(hidden = true) @MemberAuth AuthContext authContext) {
        Long memberId = authContext.id();
        cartService.entryMyCart(cartEntryRequest,memberId);
        CartEntryResponse responseData = CartEntryResponse.create();
        return ResponseEntity.ok(ApiResponse.create(4001,responseData));
    }


    @GetMapping
    @Operation(summary = "장바구니 목록 확인 API", description = "로그인한 회원의 장바구니에 상품들 확인 및 품절여부를 확인시켜주는 API"
    ,security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<List<CartFindResponse>>> findMyCart(@Parameter(hidden = true) @MemberAuth AuthContext authContext) {

        Long memberId = authContext.id();
        List<CartFindResponse> myCartList = cartService.findMyCart(memberId);
        return ResponseEntity.ok(ApiResponse.create(4000,myCartList));
    }

    @DeleteMapping
    @Operation(summary = "장바구니 삭제 API", description = "로그인한 회원의 장바구니 목록에서 삭제할 장바구니를 선택 시 삭제시키는 API"
    ,security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<CartDeleteResponse>> deleteMyCart(@Valid @RequestBody CartDeleteRequest cartDeleteRequest,
                                                                        @Parameter(hidden = true) @MemberAuth AuthContext authContext) {
        Long memberId = authContext.id();
        System.out.println(cartDeleteRequest.getDeleteId());
        cartService.deleteMyCart(cartDeleteRequest,memberId);
        CartDeleteResponse responseData = CartDeleteResponse.create();
        return ResponseEntity.ok(ApiResponse.create(4003,responseData));
    }
}
