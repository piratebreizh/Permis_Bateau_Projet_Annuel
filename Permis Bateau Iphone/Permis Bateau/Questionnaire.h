//
//  Questionnaire.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 03/01/2015.
//  Copyright (c) 2015 Alexandre Dubois. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Questionnaire : NSObject

@property (nonatomic,strong) NSArray *listeQuestion;
@property (nonatomic,strong) NSMutableArray *listeReponse;

-(id) init;

@end
