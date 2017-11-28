package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.JsonString;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ACE-PC on 2016/4/6.
 */
public class TraceIssue {


    /**
     * issues : ["20170517-058","20170517-059","20170517-060","20170517-061","20170517-062","20170517-063","20170517-064","20170517-065","20170517-066","20170517-067","20170517-068","20170517-069","20170517-070","20170517-071","20170517-072","20170517-073","20170517-074","20170517-075","20170517-076","20170517-077","20170517-078","20170517-079","20170517-080","20170517-081","20170517-082","20170517-083","20170517-084","20170517-085","20170517-086","20170517-087","20170517-088","20170517-089","20170517-090","20170517-091","20170517-092","20170517-093","20170517-094","20170517-095","20170517-096","20170517-097","20170517-098","20170517-099","20170517-100","20170517-101","20170517-102","20170517-103","20170517-104","20170517-105","20170517-106","20170517-107","20170517-108","20170517-109","20170517-110","20170517-111","20170517-112","20170517-113","20170517-114","20170517-115","20170517-116","20170517-117","20170517-118","20170517-119","20170517-120","20170518-001","20170518-002","20170518-003","20170518-004","20170518-005","20170518-006","20170518-007","20170518-008","20170518-009","20170518-010","20170518-011","20170518-012","20170518-013","20170518-014","20170518-015","20170518-016","20170518-017","20170518-018","20170518-019","20170518-020","20170518-021","20170518-022","20170518-023","20170518-024","20170518-025","20170518-026","20170518-027","20170518-028","20170518-029","20170518-030","20170518-031","20170518-032","20170518-033","20170518-034","20170518-035","20170518-036","20170518-037","20170518-038","20170518-039","20170518-040","20170518-041","20170518-042","20170518-043","20170518-044","20170518-045","20170518-046","20170518-047","20170518-048","20170518-049","20170518-050","20170518-051","20170518-052","20170518-053","20170518-054","20170518-055","20170518-056","20170518-057","20170518-058","20170518-059","20170518-060","20170518-061","20170518-062","20170518-063","20170518-064","20170518-065","20170518-066","20170518-067","20170518-068","20170518-069","20170518-070","20170518-071","20170518-072","20170518-073","20170518-074","20170518-075","20170518-076","20170518-077","20170518-078","20170518-079","20170518-080","20170518-081","20170518-082","20170518-083","20170518-084","20170518-085","20170518-086","20170518-087","20170518-088","20170518-089","20170518-090","20170518-091","20170518-092","20170518-093","20170518-094","20170518-095","20170518-096","20170518-097","20170518-098","20170518-099","20170518-100","20170518-101","20170518-102","20170518-103","20170518-104","20170518-105","20170518-106","20170518-107","20170518-108","20170518-109","20170518-110","20170518-111","20170518-112","20170518-113","20170518-114","20170518-115","20170518-116","20170518-117","20170518-118","20170518-119","20170518-120","20170519-001","20170519-002","20170519-003","20170519-004","20170519-005","20170519-006","20170519-007","20170519-008","20170519-009","20170519-010","20170519-011","20170519-012","20170519-013","20170519-014","20170519-015","20170519-016","20170519-017","20170519-018","20170519-019","20170519-020","20170519-021","20170519-022","20170519-023","20170519-024","20170519-025","20170519-026","20170519-027","20170519-028","20170519-029","20170519-030","20170519-031","20170519-032","20170519-033","20170519-034","20170519-035","20170519-036","20170519-037","20170519-038","20170519-039","20170519-040","20170519-041","20170519-042","20170519-043","20170519-044","20170519-045","20170519-046","20170519-047","20170519-048","20170519-049","20170519-050","20170519-051","20170519-052","20170519-053","20170519-054","20170519-055","20170519-056","20170519-057"]
     * basePrize : 566.6667
     * maxPrize : 646.66670470588
     * prizeLimit : 400000
     * minRebateGaps : [{"from":"0","to":"0.12","gap":"0.005"},{"from":"0.12","to":"0.128","gap":"0.001"}]
     * maxCombPrize : 2000
     * prizeRate : 0.85
     * rebate : 0.120
     */

    @SerializedName("basePrize")
    private double basePrize;
    @SerializedName("maxPrize")
    private double maxPrize;
    @SerializedName("prizeLimit")
    private double prizeLimit;
    @SerializedName("maxCombPrize")
    private int maxCombPrize;
    @SerializedName("prizeRate")
    private double prizeRate;
    @SerializedName("rebate")
    private JsonString rebate;
    @SerializedName("issues")
    private List<String> issues;
    @SerializedName("minRebateGaps")
    private List<MinRebateGaps> minRebateGaps;

    public double getBasePrize() {
        return basePrize;
    }

    public void setBasePrize(double basePrize) {
        this.basePrize = basePrize;
    }

    public double getMaxPrize() {
        return maxPrize;
    }

    public void setMaxPrize(double maxPrize) {
        this.maxPrize = maxPrize;
    }

    public double getPrizeLimit() {
        return prizeLimit;
    }

    public void setPrizeLimit(double prizeLimit) {
        this.prizeLimit = prizeLimit;
    }

    public int getMaxCombPrize() {
        return maxCombPrize;
    }

    public void setMaxCombPrize(int maxCombPrize) {
        this.maxCombPrize = maxCombPrize;
    }

    public double getPrizeRate() {
        return prizeRate;
    }

    public void setPrizeRate(double prizeRate) {
        this.prizeRate = prizeRate;
    }

    public JsonString getRebate() {
        return rebate;
    }

    public void setRebate(JsonString rebate) {
        this.rebate = rebate;
    }

    public List<String> getIssues() {
        return issues;
    }

    public void setIssues(List<String> issues) {
        this.issues = issues;
    }

    public List<MinRebateGaps> getMinRebateGaps() {
        return minRebateGaps;
    }

    public void setMinRebateGaps(List<MinRebateGaps> minRebateGaps) {
        this.minRebateGaps = minRebateGaps;
    }
}