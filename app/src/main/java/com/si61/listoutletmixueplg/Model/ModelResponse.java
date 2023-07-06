package com.si61.listoutletmixueplg.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelOutlet> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelOutlet> getData() {
        return data;
    }
}
