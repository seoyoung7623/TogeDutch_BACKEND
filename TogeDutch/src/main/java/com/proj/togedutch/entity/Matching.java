package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Matching {
    int MatchingId;
    int UserFirstId;
    int UserSecondId;
    int UserThirdId;
    int count;
}
