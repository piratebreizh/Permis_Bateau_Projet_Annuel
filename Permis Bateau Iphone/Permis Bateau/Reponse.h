//
//  Reponse.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 17/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Reponse : NSObject

@property (nonatomic, retain) NSNumber *idQuestion;
@property BOOL reponseA;
@property BOOL reponseB;
@property BOOL reponseC;
@property BOOL reponseD;
-(id) init;

@end
