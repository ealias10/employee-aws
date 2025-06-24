package iqness.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import iqness.exception.*;
import iqness.request.AssetsAllocationsCreateRequest;
import iqness.request.AssetsCreateRequest;
import iqness.request.EmployeeCreateRequest;
import iqness.service.AssetsService;
import iqness.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@Validated
@RequestMapping("/assets")
public class AssetsController {

    @Autowired
    private AssetsService assetsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseVO<Object>> createAssets(
            @RequestPart("body") String request,@RequestPart("image")List<MultipartFile> image) throws EmployeeExistsException, ParseException, IOException, ImageExistsException, AssetsExistsException {
        ResponseVO<Object> response = new ResponseVO<>();
        var objectMapper = new ObjectMapper();
         AssetsVO assetsVO = assetsService.createAssets(objectMapper.readValue(request, AssetsCreateRequest.class),image);
        response.addData(assetsVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{assetsId}/delete")
    public ResponseEntity<ResponseVO<Object>> deleteAssets(@PathVariable("assetsId") UUID assetsId) throws AssetsNotFoundException, AssetsExistsException {
        ResponseVO<Object> response = new ResponseVO<>();
        assetsService.deleteAssets(assetsId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{assetsId}/details")
    public ResponseEntity<ResponseVO<Object>> getAssetsById(@PathVariable("assetsId") UUID assetsId) throws  AssetsNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        AssetsVO assetsVO = assetsService.getAssetsById(assetsId);
        response.addData(assetsVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{assetsId}/update")
    public ResponseEntity<ResponseVO<Object>> updateAssets(@PathVariable("assetsId") UUID assetsId, @RequestBody(required = true) @Valid AssetsCreateRequest request) throws AssetsExistsException, AssetsNotFoundException, ParseException {
        ResponseVO<Object> response = new ResponseVO<>();
        AssetsVO assetsVO = assetsService.updateAssets(request, assetsId);
        response.addData(assetsVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseVO<AssetsVO>> listAssets(@Parameter(description = "offset, start from 1")
                                                               @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                               @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                               @RequestParam(value = "search", required = false) String search,
                                                               @RequestParam(value = "filter", required = false) String filter) {
        ResponseVO<AssetsVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<AssetsVO> paginatedResponseVOAndCount = assetsService.listAssets(offset, limit, search,filter);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/listByEmployee")
    public ResponseEntity<ResponseVO<AssetsVO>> listAssetsByEmployee(@Parameter(description = "offset, start from 1")
                                                           @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                           @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                           @RequestParam(value = "search", required = false) String search) {
        ResponseVO<AssetsVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<AssetsVO> paginatedResponseVOAndCount = assetsService.listAssetsByEmployee(offset, limit, search);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/details")
    public ResponseEntity<ResponseVO<Object>> getAllAssets()  {
        ResponseVO<Object> response = new ResponseVO<>();
        List<AssetsVO> assetsVO = assetsService.getAssetsList();
        response.addData(assetsVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/assetsallocations/create")
    public ResponseEntity<ResponseVO<Object>> createAssetsAllocations(
            @RequestBody(required = true) @Valid AssetsAllocationsCreateRequest request) throws AssetsExistsException, AssetsNotFoundException, EmployeeNotFoundException, AssetsAllocationsExistsException, ParseException {
        ResponseVO<Object> response = new ResponseVO<>();
        AssetsAllocationsVO assetsAllocationsVO = assetsService.createAssetsAllocations(request);
        response.addData(assetsAllocationsVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/assetsallocations/{assetsId}/update")
    public ResponseEntity<ResponseVO<Object>> updateAssetsAllocations(
            @PathVariable("assetsId") UUID assetsId) throws AssetsNotFoundException, EmployeeNotFoundException, AssetsAllocationsExistsException, ParseException, AssetsAllocationsNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        AssetsAllocationsVO assetsAllocationsVO = assetsService.updateAssetsAllocations(assetsId);
        response.addData(assetsAllocationsVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/assetsallocations/{assetsAllocationsId}/details")
    public ResponseEntity<ResponseVO<Object>> getAssetsAllocationsById(@PathVariable("assetsAllocationsId") UUID assetsAllocationsId) throws AssetsAllocationsNotFoundException, ParseException {
        ResponseVO<Object> response = new ResponseVO<>();
        AssetsAllocationsVO assetsAllocationsVO = assetsService.getAssetsAllocationsById(assetsAllocationsId);
        response.addData(assetsAllocationsVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/assetsallocations/list")
    public ResponseEntity<ResponseVO<AssetsAllocationsVO>> listAssetsAllocations(@Parameter(description = "offset, start from 1")
                                                                                 @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                                                 @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                                                 @RequestParam(value = "search", required = false) String search) {
        ResponseVO<AssetsAllocationsVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<AssetsAllocationsVO> paginatedResponseVOAndCount = assetsService.listAssetsAllocations(offset, limit, search);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/assetsallocations/listByUser")
    public ResponseEntity<ResponseVO<AssetsAllocationsVO>> listAssetsAllocationsByUser(@Parameter(description = "offset, start from 1")
                                                                                 @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                                                 @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                                                 @RequestParam(value = "search", required = false) String search) {
        ResponseVO<AssetsAllocationsVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<AssetsAllocationsVO> paginatedResponseVOAndCount = assetsService.listAssetsAllocationsByUser(offset, limit, search);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
