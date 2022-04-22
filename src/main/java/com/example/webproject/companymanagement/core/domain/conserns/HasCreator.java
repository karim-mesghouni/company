package com.example.webproject.companymanagement.core.domain.conserns;

import com.example.webproject.companymanagement.domain.app_user.models.User;

/**
 * Represents an entity which has a "createdBy" association<br>
 * which can be obtained with {@link #getCreator()}
 */
public interface HasCreator {
    User getCreator();
}
