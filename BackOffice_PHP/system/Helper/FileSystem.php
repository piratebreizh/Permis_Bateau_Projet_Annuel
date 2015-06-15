<?php

namespace FSF\Helper;

class FileSystem
{
    public static function createDirectoriesTree($base_path, $directories = '')
    {
        if ($directories && is_dir($base_path)) {
            $tmp = explode('/', $directories);
            foreach ($tmp as $el) {
                if (trim($el)) {
                    if (!is_dir($base_path . $el)) {
                        mkdir($base_path . $el, 0777);
                        chmod($base_path . $el, 0777);
                    }
                    $base_path .= $el . '/';
                }
            }

            return true;
        }

        return false;
    }
}