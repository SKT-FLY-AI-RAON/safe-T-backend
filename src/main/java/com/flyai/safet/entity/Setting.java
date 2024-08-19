package com.flyai.safet.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "settings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settings_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "warning_option")
    @ColumnDefault("1") // true:영상 띄우기, false: 소리로만 경고
    private Boolean video_option;

    @Builder
    public Setting(User user, Boolean video_option) {
        this.user = user;
        this.video_option = video_option;
    }
}