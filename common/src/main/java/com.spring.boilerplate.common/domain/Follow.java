package com.spring.boilerplate.common.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "FOLLOW_ID", columnDefinition = "int")
    private Integer followId;

    @Column(name = "FOLLOWER_ID")
    private Integer followerId;

    @Column(name = "FOLLOWING_ID")
    private Integer followingId;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "FOLLOWED_AT")
    private Date followedAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "UNFOLLOWED_AT")
    private Date unfollowedAt;

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Integer getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Integer followingId) {
        this.followingId = followingId;
    }

    public Date getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(Date followedAt) {
        this.followedAt = followedAt;
    }

    public Date getUnfollowedAt() {
        return unfollowedAt;
    }

    public void setUnfollowedAt(Date unfollowedAt) {
        this.unfollowedAt = unfollowedAt;
    }
}
