//
//  Reponse.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 17/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "Reponse.h"

@implementation Reponse

-(id) init{
    self = [super init];
    self.idQuestion = 0;
    self.reponseA = NO;
    self.reponseB = NO;
    self.reponseC = NO;
    self.reponseD = NO;
    return self;
}

@end
