package com.prime.redef.multitype;

import androidx.annotation.IntRange;

public interface IMultiTypeItem {

    @IntRange(from = 1, to = 65535)
    int getListItemType();
}