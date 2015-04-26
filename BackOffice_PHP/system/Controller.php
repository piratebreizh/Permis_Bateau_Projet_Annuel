<?php

namespace FSF;

use FSF\Routing\Request;

class Controller
{
    /** @var  Request */
    protected $request;

    /**
     * @param Request $request
     */
    public function __construct($request)
    {
        $this->request = $request;
    }

    /**
     * @return Request
     */
    public function getRequest()
    {
        return $this->request;
    }
}