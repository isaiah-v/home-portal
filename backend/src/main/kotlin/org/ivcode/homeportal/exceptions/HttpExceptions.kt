package org.ivcode.homeportal.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException: RuntimeException()

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException: RuntimeException()

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException: RuntimeException()

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException: RuntimeException()

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException: RuntimeException()

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException: RuntimeException()