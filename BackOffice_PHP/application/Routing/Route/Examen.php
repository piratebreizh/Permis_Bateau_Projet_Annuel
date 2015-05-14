<?php

namespace APP\Routing\Route;

use FSF\Routing\Route;

class Examen extends Route
{

    /**
     * Must return true if URI can be handled by route
     * @return bool
     */
    public function canHandle()
    {
        $request = $this->getRequest()->getRequestUri();
        if (preg_match("~^/examen/(.+)~", $request, $matches)) {
            if (isset($matches[1])) {
                $this->setAction($matches[1]);
                if (method_exists($this->getController(), $this->getAction()))
                    return true;
            }
        }
        return false;
    }

    /**
     * @return string
     */
    public function getController()
    {
        if ($this->controller == null) {
            $this->controller = new \APP\Module\Examen\Controller($this->getRequest());
        }

        return $this->controller;
    }
}