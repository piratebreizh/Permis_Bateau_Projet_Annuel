<?php

namespace FSF\Helper;

class Date
{

    /**
     * Convert a date with FR format dd/mm/yyyy to date in UK format
     *
     * @param string $date_fr Date in FR format (with or without hours)
     *
     * @return string Date in UK format (yyyy-mm-dd)
     */
    public static function frToUk($date_fr)
    {
        // If it is a correct formated date
        if (self::isDateTime($date_fr)) {
            if (stripos($date_fr, ' ') === false) {
                $hours = '';
                list($day, $month, $year) = preg_split('|[/.-]|', $date_fr);
            } else {
                list($date, $hours) = explode(' ', $date_fr);
                list($day, $month, $year) = preg_split('|[/.-]|', $date);
                $hours = ' ' . $hours;
            }
            //If year is 2 digits => convert to 4 digits
            if (strlen($year) == 2) {
                $year = '20' . $year;
            }
            $date_uk = $year . '-' . $month . '-' . $day . $hours;

            // If already converted
        } elseif (self::isDateTimeUk($date_fr)) {
            $date_uk = $date_fr;
        } else {
            $date_uk = '';
        }

        return $date_uk;
    }

    /**
     * Convert a date with UK format to date in FR format (dd/mm/yyyy)
     *
     * @param string $date_uk Date in UK format (with or without hours)
     *
     * @return string Date in FR format (dd/mm/yyyy)
     */
    public static function ukToFr($date_uk)
    {
        // If it is a correct formated date
        if (self::isDateTimeUk($date_uk)) {
            if (stripos($date_uk, ' ') === false) {
                $hours = '';
                list($year, $month, $day) = preg_split('|[/.-]|', $date_uk);
            } else {
                list($date, $hours) = explode(' ', $date_uk);
                list($year, $month, $day) = preg_split('|[/.-]|', $date);
                $hours = ' ' . $hours;
            }
            //If year is 2 digits => convert to 4 digits
            if (strlen($year) == 2) {
                $year = '20' . $year;
            }
            $date_fr = $day . '/' . $month . '/' . $year . $hours;

            // If already converted
        } elseif (self::isDateTime($date_uk)) {
            $date_fr = $date_uk;
        } else {
            $date_fr = false;
        }

        return $date_fr;
    }


    /**
     * Validate that a Datetime or date is FR format
     * with separators [./-]
     *
     * @param string $value
     *
     * @return bool
     */
    public static function isDateTime($value)
    {
        $test = false;
        if (is_string($value)) {
            $result = preg_split('|\ |', $value);
            $test = self::isDate($result[0]);
            // If datetime
            if (count($result) == 2) {
                $test = $test && self::isHours($result[1]);
            }
        }

        return $test;
    }

    /**
     * Validate that the Datetime or date given is UK format
     * with separators [./-]
     *
     * @param string $value
     *
     * @return bool
     */
    public static function isDateTimeUk($value)
    {
        $test = false;
        if (is_string($value)) {
            $result = preg_split('|\ |', $value);
            $test = self::isDateUk($result[0]);
            // If datetime
            if (count($result) == 2) {
                $test = $test && self::heure($result[1]);
            }
        }

        return $test;
    }


    /**
     * Validate that the date given is in FR format (dd/mm/YYYY)
     * with separators [./-]
     *
     * @param mixed $value
     *
     * @return bool
     */
    public static function isDate($value)
    {
        if (is_string($value)) {
            $split = preg_split('|[/.-]|', $value);
            if (count($split) == 3) {
                list($day, $month, $year) = $split;
                $day = intval($day);
                $month = intval($month);
                $year = intval($year);
                if (strlen($year) == 2) {
                    $year = '20' . $year;
                }
                if ($year < 1000) {
                    return false;
                }
                if ($year > 9999) {
                    return false;
                }

                return checkDate($month, $day, $year);
            }
        }

        return false;
    }

    /**
     * Validate that the date given is in UK format (YYYY-mm-dd)
     * with separators [./-]
     *
     * @param mixed $value
     *
     * @return bool
     */
    public static function isDateUk($value)
    {
        if (is_string($value)) {
            $split = preg_split('|[/.-]|', $value);
            if (count($split) == 3) {
                list($year, $month, $day) = $split;
                $day = intval($day);
                $month = intval($month);
                $year = intval($year);
                if (strlen($year) == 2) {
                    $year = '20' . $year;
                }
                if ($year < 1000) {
                    return false;
                }
                if ($year > 9999) {
                    return false;
                }

                return checkDate($month, $day, $year);
            }
        }

        return false;
    }

    /**
     * Validate that the given value is hours
     * @param $value
     * @return bool
     */
    public static function isHours($value)
    {
        if (is_string($value)) {
            return (bool)preg_match('#^(([01][0-9])|(2[0-3]))(:[0-5][0-9])(:[0-5][0-9])?$#', $value);
        }

        return false;
    }

    /**
     * @param $value
     * @return string
     */
    public static function blockToFr($value)
    {
        $date_fr = "";
        if(strlen($value) == 14){
            $day = substr($value, 0, 2);
            $month = substr($value, 2, 2);
            $year = substr($value, 4, 4);
            $hours = substr($value, 8, 2);
            $minutes = substr($value, 10, 2);
            $secunds = substr($value, 12, 2);

            $date_fr = "{$day}/{$month}/{$year} {$hours}:{$minutes}:{$secunds}";
            if(!self::isDateTime($date_fr))
                $date_fr = "";
        }
        
        return $date_fr;
    }
    
}