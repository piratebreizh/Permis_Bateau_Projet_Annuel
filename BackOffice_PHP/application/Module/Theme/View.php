<?php

namespace APP\Module\Theme;

class View extends \FSF\View
{
    static public function getPath()
    {
        return __DIR__ . DIRECTORY_SEPARATOR . 'View'. DIRECTORY_SEPARATOR;
    }
}