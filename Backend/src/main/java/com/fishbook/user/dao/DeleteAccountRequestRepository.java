package com.fishbook.user.dao;

import com.fishbook.user.model.DeleteAccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteAccountRequestRepository extends JpaRepository<DeleteAccountRequest, Long> {

}
