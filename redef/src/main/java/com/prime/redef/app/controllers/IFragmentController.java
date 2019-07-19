package com.prime.redef.app.controllers;

import androidx.annotation.Nullable;

import com.prime.redef.app.RedefFragment;

public interface IFragmentController {
    interface ITransaction {
        ITransactionBuilder useFragment(Class<? extends RedefFragment> e);
        void commit();
    }
    interface ITransactionBuilder {
        ITransactionBuilder withParameter(@Nullable Object o);
        ITransactionBuilder withTitle(String title);
        ITransactionBuilder useFragment(Class<? extends RedefFragment> type);
        void commit();
    }

    ITransaction startTransaction();
    void commitTransaction(ITransactionBuilder[] transactionBuilders);

    int getCurrentTab();
    void setCurrentTab(int position);
}
