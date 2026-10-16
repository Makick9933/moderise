package com.yourname.mchelper;

import java.util.*;

public class Rules {
    public static class Rule {
        public final String code;
        public final String name;
        public final String duration;
        public final boolean isBan;

        public Rule(String code, String name, String duration, boolean isBan) {
            this.code = code;
            this.name = name;
            this.duration = duration;
            this.isBan = isBan;
        }
    }

    public static final List<Rule> ALL = new ArrayList<>();

    static {
        ALL.add(new Rule("#мат", "Мат", "30м", false));
        ALL.add(new Rule("#спам", "Спам/Флуд", "15м", false));
        ALL.add(new Rule("#оск", "Оскорбление/Унижение", "2ч", false));
        ALL.add(new Rule("#оскрод", "Оскорбление родных", "6ч", false));
        ALL.add(new Rule("#амор", "Аморальное поведение 18+", "24ч", false));
        ALL.add(new Rule("#фейк", "Ложная информация", "12ч", false));
        ALL.add(new Rule("#реп", "Ложные репорты", "12ч", false));
        ALL.add(new Rule("#оскпр", "Оскорбление проекта", "14д", true));
        ALL.add(new Rule("#критика", "Критика проекта", "8ч", true));
        ALL.add(new Rule("#диск", "Разжигание розни", "48ч", true));
        ALL.add(new Rule("#рекл", "Реклама сторонних ресурсов", "навсегда", true));
        ALL.add(new Rule("#упом", "Упоминание сторонних проектов", "3д", true));
        ALL.add(new Rule("#выдадм", "Выдача себя за администрацию", "7д", true));
        ALL.add(new Rule("#обсчит", "Обсуждение читов/уязвимостей", "24ч", true));
        ALL.add(new Rule("#зазыв", "Зазыв на другой сервер", "6ч", true));
        ALL.add(new Rule("#склон", "Склонение к нарушению", "15м", true));
        ALL.add(new Rule("#читы", "Читы/Стороннее ПО", "навсегда", true));
        ALL.add(new Rule("#комчит", "Игра в команде с читером", "14д", true));
        ALL.add(new Rule("#твинк", "Обход бана (2 акк)", "навсегда", true));
        ALL.add(new Rule("#дюп", "Дюперство", "навсегда", true));
        ALL.add(new Rule("#лагоюз", "Создание лагов", "14д", true));
        ALL.add(new Rule("#таб", "Нарушение на табличке/книге", "1д", true));
        ALL.add(new Rule("#петклан", "Название клана/пета/варпа", "1д", true));
        ALL.add(new Rule("#сделка", "Продажа ценностей за деньги", "30д", true));
        ALL.add(new Rule("#передача", "Передача аккаунта/Взлом", "30д", true));
        ALL.add(new Rule("#флудком", "Флуд командами", "3ч", true));
        ALL.add(new Rule("#ник", "Ник нарушающий правила", "навсегда", true));
        ALL.add(new Rule("#некник", "Смена ника на нарушающий", "7д", true));
        ALL.add(new Rule("#багоюз", "Использование багов", "навсегда", true));
        ALL.add(new Rule("#постройки", "Постройки нарушающие правила", "7д", true));
    }

    public static Rule byCode(String code) {
        for (Rule r : ALL) if (r.code.equals(code)) return r;
        return null;
    }

    public static int durationSeconds(String code) {
        Rule r = byCode(code);
        if (r == null) return 30 * 60;
        String d = r.duration;
        if (d.contains("навсегда")) return 0;
        String num = d.replaceAll("[^0-9]", "");
        if (num.isEmpty()) return 0;
        int n = Integer.parseInt(num);
        if (d.contains("ч")) return n * 3600;
        if (d.contains("д")) return n * 86400;
        if (d.contains("м")) return n * 60;
        return 30 * 60;
    }

    public static String durationStr(int sec) {
        if (sec <= 0) return "permanent";
        if (sec < 3600) return Math.max(1, sec / 60) + "m";
        if (sec < 86400) return (int) Math.ceil(sec / 3600.0) + "h";
        return (int) Math.ceil(sec / 86400.0) + "d";
    }

    public static String durationForCombo(List<String> codes) {
        int total = 0;
        for (String c : codes) {
            int s = durationSeconds(c);
            if (s == 0) return "permanent";
            total += s;
        }
        return durationStr(total);
    }
}
