package com.smtown.smhds.board;

/**
 * <pre>
 *	StatInfo.java - 총 진행상황 정보(메인화면에 표시할 정보)
 * </pre>
 *
 * @author	최해림
 * @since	2019.07.08
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.07.08	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
public class StatInfo {
    private int stat_neww;  //신규 요청사항 갯수
    private int stat_chek;  //접수한 요청사항 갯수
    private int stat_load;  //진행중인 요청사항 갯수
    private int stat_comp;  //완료된 요청사항 갯수

    public int getStat_neww() {
       return stat_neww;
    }

    public void setStat_neww(int stat_neww) {
        this.stat_neww = stat_neww;
    }

    public int getStat_chek() {
        return stat_chek;
    }

    public void setStat_chek(int stat_chek) {
        this.stat_chek = stat_chek;
    }

    public int getStat_load() {
        return stat_load;
    }

    public void setStat_load(int stat_load) {
        this.stat_load = stat_load;
    }

    public int getStat_comp() {
        return stat_comp;
    }

    public void setStat_comp(int stat_comp) {
        this.stat_comp = stat_comp;
    }
}
