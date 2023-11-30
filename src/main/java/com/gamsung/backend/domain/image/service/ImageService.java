package com.gamsung.backend.domain.image.service;

import com.gamsung.backend.domain.image.entity.Image;
import com.gamsung.backend.domain.image.exception.ImageNotFoundException;
import com.gamsung.backend.domain.image.repository.ImageRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

}
