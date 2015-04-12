<?php

namespace FSF;


class String
{

    /**
     * Convert a string from CamelCase to under_score_case
     * @param string $string
     * @return string
     */
    public static function to_Case($string)
    {
        return strtolower(preg_replace('/([a-zA-Z])(?=[A-Z])/', '$1_$2', $string));
    }

    /**
     * Convert a string from under_score_case to CamelCase
     * @param string $string
     * @return string
     */
    public static function toCamelCase($string)
    {
        return str_replace(' ', '', ucwords(str_replace('_', ' ', $string)));
    }
}