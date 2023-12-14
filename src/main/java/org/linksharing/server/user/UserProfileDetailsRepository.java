package org.linksharing.server.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileDetailsRepository extends CrudRepository<UserProfileDetails, String> {
    UserProfileDetails findByEmail(String email);
}
